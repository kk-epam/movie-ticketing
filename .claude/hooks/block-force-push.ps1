# PreToolUse hook: blocks force pushes. Exit code 2 = block and show stderr to Claude.
$payload = [Console]::In.ReadToEnd() | ConvertFrom-Json
$cmd = $payload.tool_input.command
if ($cmd -match 'git\s+push\b.*(--force|--force-with-lease|\s-f\b)') {
    [Console]::Error.WriteLine('Blocked: force push is not allowed in this project.')
    exit 2
}
exit 0
