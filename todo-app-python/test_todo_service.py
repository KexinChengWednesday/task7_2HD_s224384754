"""Unit tests for TodoService (pytest)."""

import pytest
from todo_service import TodoService


@pytest.fixture
def service():
    """Provide a fresh service instance for each test."""
    return TodoService()


def test_create_todo(service):
    todo = service.create("Buy milk")
    assert todo["id"] == 1
    assert todo["title"] == "Buy milk"
    assert todo["completed"] is False


def test_create_multiple_todos(service):
    service.create("Task 1")
    service.create("Task 2")
    service.create("Task 3")
    assert service.count() == 3


def test_get_all(service):
    service.create("A")
    service.create("B")
    assert len(service.get_all()) == 2


def test_complete_todo(service):
    todo = service.create("Read book")
    completed = service.complete(todo["id"])
    assert completed["completed"] is True


def test_delete_todo(service):
    todo = service.create("Delete me")
    assert service.delete(todo["id"]) is True
    assert service.count() == 0


def test_empty_title_raises(service):
    with pytest.raises(ValueError):
        service.create("")


def test_none_title_raises(service):
    with pytest.raises(ValueError):
        service.create(None)


# Intentionally failing test for CI/CD demonstration
def test_intentionally_failing(service):
    todo = service.create("This will fail")
    assert todo["id"] == 999  # Real id is 1, so this fails