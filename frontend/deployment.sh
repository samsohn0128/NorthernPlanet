npm run build
docker cp build f66d:/app
docker container restart f66d
