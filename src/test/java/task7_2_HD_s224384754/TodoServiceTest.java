package task7_2_HD_s224384754;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {
    private TodoService service;

    @BeforeEach
    void setUp() {
        service = new TodoService();
    }

    @Test
    void testCreateTodo() {
        Todo todo = service.create("Buy milk");
        assertNotNull(todo);
        assertEquals("Buy milk", todo.getTitle());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testCreateMultipleTodos() {
        service.create("Task 1");
        service.create("Task 2");
        service.create("Task 3");
        assertEquals(3, service.count());
    }

    @Test
    void testGetAll() {
        service.create("A");
        service.create("B");
        List<Todo> all = service.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testCompleteTodo() {
        Todo todo = service.create("Read book");
        Todo completed = service.complete(todo.getId());
        assertTrue(completed.isCompleted());
    }

    @Test
    void testDeleteTodo() {
        Todo todo = service.create("Delete me");
        assertTrue(service.delete(todo.getId()));
        assertEquals(0, service.count());
    }

    @Test
    void testEmptyTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.create(""));
    }

    @Test
    void testNullTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    // fail test->correct
    @Test
    void testIntentionallyFailing() {
        Todo todo = service.create("This will fail");
        assertEquals(999L, todo.getId()); // real id is 1
    }
}
