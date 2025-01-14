param (
    [string]$Message
)

if (-not $Message) {
    Write-Host "Error: Commit message is required." -ForegroundColor Red
    exit 1
}

# Stage changes
git add .

# Commit with the provided message
git commit -m $Message

# Push changes to the repository
git push