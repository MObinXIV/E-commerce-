# Basic commands 

- explore the container’s filesystem, run commands like psql-> docker exec -it <container name (which we get using docker ps command)  bash
- docker exec -it postgres_sql-ecommerce  psql -U username -d ecommerce -> got inside db