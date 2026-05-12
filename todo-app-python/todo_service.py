"""Business logic for managing todos."""


class TodoService:
    """In-memory CRUD service for Todo items."""

    def __init__(self):
        # All todos stored as {id: dict}
        self._todos = {}
        self._next_id = 1

    def create(self, title):
        """Create a new todo with the given title."""
        if title is None or title.strip() == "":
            raise ValueError("Title cannot be empty")
        todo = {
            "id": self._next_id,
            "title": title,
            "completed": False,
        }
        self._todos[self._next_id] = todo
        self._next_id += 1
        return todo

    def get_all(self):
        """Return all todos as a list."""
        return list(self._todos.values())

    def get_by_id(self, todo_id):
        """Return a todo by its id, or raise KeyError if not found."""
        if todo_id not in self._todos:
            raise KeyError(f"Todo not found: {todo_id}")
        return self._todos[todo_id]

    def complete(self, todo_id):
        """Mark a todo as completed."""
        todo = self.get_by_id(todo_id)
        todo["completed"] = True
        return todo

    def delete(self, todo_id):
        """Delete a todo by id, return True if deleted."""
        if todo_id in self._todos:
            del self._todos[todo_id]
            return True
        return False

    def count(self):
        """Return the number of stored todos."""
        return len(self._todos)