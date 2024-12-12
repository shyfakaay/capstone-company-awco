# Menggunakan image Node.js sebagai base image
FROM node:18

# Set working directory
WORKDIR /usr/src/app

# Menyalin package.json dan package-lock.json
COPY package*.json ./

# Menginstall dependencies
RUN npm install

# Menyalin semua file ke dalam container
COPY . .

# Mengatur port yang akan digunakan
ENV PORT=8080

# Menjalankan aplikasi
CMD ["npm", "start"]