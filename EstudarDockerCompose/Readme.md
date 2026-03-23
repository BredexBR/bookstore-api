# Estudos docker-compose
Estudar como criar um docker composer, boas práticas, comandos terminal.
Para isso seria criado um banco de dados e uma aplicação wordpress.


## Comandos Terminal:
`OBS:` Se estiver em ambiente windows o Docker-Desktop precisa estar iniciado.

Para ver **todos** os contâiners docker em execução:
```bash
docker ps
```

Para visualizar somente os contâiner vinculados ao docker-compose:
```bash
docker-compose ps
```

Para rodar o docker compose:
```bash
docker-compose up
```

- Para iniciar o docker composer em backGround(detachado):
```bash
docker-compose up -d
```

- Para desativar o contâiner:
```bash
docker-compose down
```

## Status Code:
- `0:` O ciclo de vida do Container foi encerrado com sucesso.
- `Diferente de 0:` O contêiner foi encarrado por que ocorreu um erro!