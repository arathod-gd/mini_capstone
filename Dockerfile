version: '3.8'

services:
  database:
    image: postgres:15
    container_name: mini_capstone_db
    restart: always

    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}

    ports:
      - "${POSTGRES_PORT}:5432"

    volumes:
      - mini_capstone_data:/var/lib/postgresql/data

volumes:
  mini_capstone_data: