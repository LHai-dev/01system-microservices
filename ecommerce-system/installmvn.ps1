$ErrorActionPreference = "Stop"

# =========================================================
# CONFIG
# =========================================================

$mavenVersion = "3.9.16"

$installRoot = "C:\tools"
$mavenHome = "$installRoot\apache-maven-$mavenVersion"
$mavenBin = "$mavenHome\bin"

$zipFile = "$env:TEMP\apache-maven-$mavenVersion-bin.zip"

$downloadUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"


# =========================================================
# HELPER: FIND REAL JAVA JDK
# =========================================================

function Find-JavaHome {

    Write-Host ""
    Write-Host "Searching for installed JDK..."

    # -----------------------------------------------------
    # 1. Try JAVA_HOME if already configured correctly
    # -----------------------------------------------------

    if ($env:JAVA_HOME) {

        $javaFromEnv = Join-Path $env:JAVA_HOME "bin\java.exe"
        $javacFromEnv = Join-Path $env:JAVA_HOME "bin\javac.exe"

        if (
        (Test-Path $javaFromEnv) -and
                (Test-Path $javacFromEnv)
        ) {
            Write-Host "Found JDK from existing JAVA_HOME:"
            Write-Host $env:JAVA_HOME

            return $env:JAVA_HOME
        }
    }


    # -----------------------------------------------------
    # 2. Search Oracle / Java standard installation folders
    # -----------------------------------------------------

    $javaFolders = @(
        "C:\Program Files\Java",
        "C:\Program Files\Eclipse Adoptium",
        "C:\Program Files\Microsoft",
        "C:\Program Files\Amazon Corretto",
        "C:\Program Files\Zulu"
    )

    foreach ($baseFolder in $javaFolders) {

        if (-not (Test-Path $baseFolder)) {
            continue
        }

        $jdkFolders = Get-ChildItem `
            -Path $baseFolder `
            -Directory `
            -ErrorAction SilentlyContinue |
                Sort-Object LastWriteTime -Descending

        foreach ($jdk in $jdkFolders) {

            $javaExe = Join-Path $jdk.FullName "bin\java.exe"
            $javacExe = Join-Path $jdk.FullName "bin\javac.exe"

            if (
            (Test-Path $javaExe) -and
                    (Test-Path $javacExe)
            ) {

                Write-Host "Found JDK:"
                Write-Host $jdk.FullName

                return $jdk.FullName
            }
        }
    }


    # -----------------------------------------------------
    # 3. Try Windows Registry
    # -----------------------------------------------------

    $registryPaths = @(
        "HKLM:\SOFTWARE\JavaSoft\JDK",
        "HKLM:\SOFTWARE\JavaSoft\Java Development Kit",
        "HKLM:\SOFTWARE\WOW6432Node\JavaSoft\JDK",
        "HKLM:\SOFTWARE\WOW6432Node\JavaSoft\Java Development Kit"
    )

    foreach ($registryPath in $registryPaths) {

        if (-not (Test-Path $registryPath)) {
            continue
        }

        try {

            $currentVersion = (
            Get-ItemProperty $registryPath
            ).CurrentVersion

            if ($currentVersion) {

                $versionPath = Join-Path $registryPath $currentVersion

                if (Test-Path $versionPath) {

                    $javaHome = (
                    Get-ItemProperty $versionPath
                    ).JavaHome

                    if ($javaHome) {

                        $javaExe = Join-Path $javaHome "bin\java.exe"
                        $javacExe = Join-Path $javaHome "bin\javac.exe"

                        if (
                        (Test-Path $javaExe) -and
                                (Test-Path $javacExe)
                        ) {

                            Write-Host "Found JDK from registry:"
                            Write-Host $javaHome

                            return $javaHome
                        }
                    }
                }
            }

        }
        catch {
            # Ignore registry errors
        }
    }


    throw @"
Could not find a valid JDK installation.

Java runtime may exist, but Maven needs a JDK.

Check these commands:

where.exe java
where.exe javac

Also check:

C:\Program Files\Java

JAVA_HOME must point to the JDK root, for example:

C:\Program Files\Java\jdk-25

NOT:

C:\Program Files\Java\jdk-25\bin

and NOT:

C:\Program Files\Common Files\Oracle\Java\javapath
"@
}


# =========================================================
# JAVA CONFIGURATION
# =========================================================

Write-Host "========================================="
Write-Host "Configuring Java"
Write-Host "========================================="

$javaHome = Find-JavaHome

$javaBin = "$javaHome\bin"

Write-Host ""
Write-Host "JAVA_HOME:"
Write-Host $javaHome

# Save JAVA_HOME permanently for current user

[Environment]::SetEnvironmentVariable(
        "JAVA_HOME",
        $javaHome,
        "User"
)

# Set JAVA_HOME for current PowerShell session

$env:JAVA_HOME = $javaHome


# =========================================================
# VERIFY JAVA
# =========================================================

Write-Host ""
Write-Host "Checking Java..."

& "$javaHome\bin\java.exe" -version

Write-Host ""
Write-Host "Checking Java compiler..."

& "$javaHome\bin\javac.exe" -version


# =========================================================
# DOWNLOAD MAVEN
# =========================================================

Write-Host ""
Write-Host "========================================="
Write-Host "Installing Maven $mavenVersion"
Write-Host "========================================="

if (Test-Path $zipFile) {

    Write-Host "Removing previous Maven ZIP..."

    Remove-Item `
        -Path $zipFile `
        -Force
}

Write-Host ""
Write-Host "Downloading Maven..."

curl.exe `
    --fail `
    --location `
    --retry 3 `
    --output $zipFile `
    $downloadUrl

if (-not (Test-Path $zipFile)) {
    throw "Maven ZIP was not downloaded."
}


# =========================================================
# VALIDATE DOWNLOAD
# =========================================================

$fileSize = (Get-Item $zipFile).Length

Write-Host ""
Write-Host "Downloaded size: $fileSize bytes"

if ($fileSize -lt 1MB) {

    Write-Host ""
    Write-Host "Downloaded file is too small."

    throw "The downloaded file is probably an HTML error page."
}


# =========================================================
# CREATE INSTALL DIRECTORY
# =========================================================

Write-Host ""
Write-Host "Creating Maven installation directory..."

New-Item `
    -ItemType Directory `
    -Force `
    -Path $installRoot |
        Out-Null


# =========================================================
# REMOVE OLD MAVEN
# =========================================================

if (Test-Path $mavenHome) {

    Write-Host "Removing previous Maven installation..."

    Remove-Item `
        -Path $mavenHome `
        -Recurse `
        -Force
}


# =========================================================
# EXTRACT MAVEN
# =========================================================

Write-Host ""
Write-Host "Extracting Maven..."

Expand-Archive `
    -Path $zipFile `
    -DestinationPath $installRoot `
    -Force

if (-not (Test-Path "$mavenBin\mvn.cmd")) {
    throw "Maven extraction failed. mvn.cmd was not found."
}


# =========================================================
# MAVEN_HOME
# =========================================================

Write-Host ""
Write-Host "Setting MAVEN_HOME..."

[Environment]::SetEnvironmentVariable(
        "MAVEN_HOME",
        $mavenHome,
        "User"
)

$env:MAVEN_HOME = $mavenHome


# =========================================================
# CONFIGURE USER PATH
# =========================================================

Write-Host ""
Write-Host "Configuring PATH..."

$userPath = [Environment]::GetEnvironmentVariable(
        "Path",
        "User"
)

if (-not $userPath) {
    $userPath = ""
}

$pathEntries = $userPath -split ";" |
        Where-Object {
            $_ -and
                    $_.Trim() -ne ""
        }


# Remove old Maven entries

$pathEntries = $pathEntries |
        Where-Object {
            $_ -notmatch "apache-maven.*\\bin"
        }


# Remove duplicate Java home bin

$pathEntries = $pathEntries |
        Where-Object {
            $_ -ne $javaBin
        }


# Add Java + Maven

$newPathEntries = @(
    $javaBin
    $mavenBin
) + $pathEntries


$newUserPath = (
$newPathEntries |
        Select-Object -Unique
) -join ";"


[Environment]::SetEnvironmentVariable(
        "Path",
        $newUserPath,
        "User"
)


# =========================================================
# CURRENT POWERSHELL SESSION PATH
# =========================================================

$env:Path = "$javaBin;$mavenBin;$env:Path"


# =========================================================
# VERIFY ENVIRONMENT VARIABLES
# =========================================================

Write-Host ""
Write-Host "========================================="
Write-Host "Environment"
Write-Host "========================================="

Write-Host ""
Write-Host "JAVA_HOME:"
Write-Host $env:JAVA_HOME

Write-Host ""
Write-Host "MAVEN_HOME:"
Write-Host $env:MAVEN_HOME


# =========================================================
# VERIFY MAVEN
# =========================================================

Write-Host ""
Write-Host "========================================="
Write-Host "Testing Maven"
Write-Host "========================================="
Write-Host ""

& "$mavenBin\mvn.cmd" -version


# =========================================================
# DONE
# =========================================================

Write-Host ""
Write-Host "========================================="
Write-Host "Installation completed successfully"
Write-Host "========================================="

Write-Host ""
Write-Host "JAVA_HOME = $javaHome"
Write-Host "MAVEN_HOME = $mavenHome"

Write-Host ""
Write-Host "IMPORTANT:"
Write-Host "Close CMD, PowerShell, Git Bash and IntelliJ terminals."
Write-Host "Then open a NEW terminal."
Write-Host ""

Write-Host "Test:"
Write-Host "java -version"
Write-Host "javac -version"
Write-Host "mvn -version"