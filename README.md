
# Run Locally

Clone the project

```bash
  git clone https://github.com/PhucApu/jakatar.git
```

## In Frontend
Go to the Frontend directory

```bash
  cd Frontend
```

Add .env in Frontend/src

```bash
  VITE_API_URL = http://localhost:8080
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run dev
```

# In Backend
In src/main/resource: 
- Import data.sql to your database
- Add .env file 
```bash
DB_HOST=localhost
DB_NAME=bus-station
DB_PORT=3306
DB_USER=<your-user>
DB_PASS=<your-pwd>
```