"""Flask HTTP server exposing the Todo REST API."""

import os
from flask import Flask, jsonify, request
from todo_service import TodoService

app = Flask(__name__)
service = TodoService()


@app.route("/", methods=["GET"])
def root():
    """Welcome endpoint."""
    return "Todo App (Python/Flask) is running! Try GET /api/todos"


@app.route("/api/todos", methods=["GET"])
def list_todos():
    """Return all todos as JSON."""
    return jsonify(service.get_all())


@app.route("/api/todos", methods=["POST"])
def create_todo():
    """Create a new todo. Accepts optional JSON {'title': ...}."""
    data = request.get_json(silent=True) or {}
    title = data.get("title", f"Sample Task {service.count() + 1}")
    try:
        todo = service.create(title)
        return jsonify(todo), 201
    except ValueError as e:
        return jsonify({"error": str(e)}), 400


@app.route("/api/todos/<int:todo_id>/complete", methods=["PUT"])
def complete_todo(todo_id):
    """Mark a todo as completed."""
    try:
        todo = service.complete(todo_id)
        return jsonify(todo)
    except KeyError as e:
        return jsonify({"error": str(e)}), 404


@app.route("/api/todos/<int:todo_id>", methods=["DELETE"])
def delete_todo(todo_id):
    """Delete a todo by id."""
    deleted = service.delete(todo_id)
    return jsonify({"deleted": deleted})


if __name__ == "__main__":
    # Cloud Run injects PORT env var; default to 8080 for local dev
    port = int(os.environ.get("PORT", 5000))
    # Bind to 0.0.0.0 so traffic from outside the container reaches Flask
    app.run(host="0.0.0.0", port=port)