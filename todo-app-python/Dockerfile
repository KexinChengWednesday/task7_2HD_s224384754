# Use official slim Python image to keep the container small
FROM python:3.11-slim

WORKDIR /app

# Copy dependency manifest first to leverage Docker layer caching
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application source
COPY . .

# Cloud Run injects PORT; expose 8080 as a hint
EXPOSE 8080

# Use gunicorn (production-grade WSGI server) instead of Flask dev server
CMD exec gunicorn --bind 0.0.0.0:${PORT:-8080} --workers 1 --threads 8 app:app