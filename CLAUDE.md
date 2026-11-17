# Movie Ticketing

Spring Boot (Maven) app: movies, shows, seat booking.

- Build/test: `mvn test`
- Layers: controller -> service -> repository (`src/main/java/com/example/ticketing/`)

## Claude config
- `.mcp.json` — project MCP servers (shared)
- `.claude/settings.json` — permissions and hooks (shared)
- `.claude/settings.local.json` — personal overrides (gitignored)
- `.claude/hooks/` — hook scripts
- `.claude/skills/<name>/SKILL.md` — project skills
