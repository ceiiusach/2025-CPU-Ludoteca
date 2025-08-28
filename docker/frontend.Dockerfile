FROM node:lts-bookworm AS build
WORKDIR /app
COPY frontend/package.json ./
RUN npm install -r --frozen-lockfile

COPY frontend /app
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/templates/default.conf.template
CMD ["nginx", "-g", "daemon off;"]