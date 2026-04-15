@echo off
setlocal enabledelayedexpansion

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "MAVEN_WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

:: Read distributionUrl
for /f "tokens=2 delims==" %%A in ('findstr /b "distributionUrl" "%MAVEN_WRAPPER_PROPERTIES%"') do set "DISTRIBUTION_URL=%%A"

:: Derive directory name from zip filename
for %%F in (%DISTRIBUTION_URL%) do set "MAVEN_ZIP_FILENAME=%%~nxF"
set "MAVEN_DIR_NAME=%MAVEN_ZIP_FILENAME:-bin.zip=%"

if not defined MAVEN_USER_HOME set "MAVEN_USER_HOME=%USERPROFILE%\.m2\wrapper"
set "MAVEN_HOME=%MAVEN_USER_HOME%\dists\%MAVEN_DIR_NAME%"
set "MAVEN_EXE=%MAVEN_HOME%\bin\mvn.cmd"

if not exist "%MAVEN_EXE%" (
  echo Downloading Maven from %DISTRIBUTION_URL% ...
  if not exist "%MAVEN_USER_HOME%\dists" mkdir "%MAVEN_USER_HOME%\dists"
  set "TMP_ZIP=%MAVEN_USER_HOME%\%MAVEN_ZIP_FILENAME%"
  powershell -Command "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '!TMP_ZIP!'"
  powershell -Command "Expand-Archive -Path '!TMP_ZIP!' -DestinationPath '%MAVEN_USER_HOME%\dists' -Force"
  del "!TMP_ZIP!"
  echo Maven installed to %MAVEN_HOME%
)

"%MAVEN_EXE%" %*
endlocal
