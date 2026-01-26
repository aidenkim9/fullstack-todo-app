import { TodoButtons } from "./TodoButtons";
import { TodoEditInput } from "./TodoEditInput";

const TodoList = ({
  todos,
  editId,
  editText,
  handleToggle,
  handleUpdate,
  handleEdit,
  handleDelete,
  setEditId,
  setEditText,
}) => {
  return (
    <>
      <ul className="space-y-3 mb-4">
        {todos.map((todo) => (
          <li
            className="flex justify-between items-center p-2 border-b border-gray-300"
            key={todo.id}
          >
            <div>
              <input
                type="checkbox"
                checked={todo.completed}
                onChange={() => handleToggle(todo.id)}
                className="mx-3"
              />

              {todo.id === editId ? (
                <TodoEditInput
                  todo={todo}
                  editText={editText}
                  setEditText={setEditText}
                  setEditId={setEditId}
                  handleUpdate={handleUpdate}
                />
              ) : (
                <span onClick={() => handleEdit(todo)}>{todo.title}</span>
              )}
            </div>
            <TodoButtons
              editId={editId}
              todo={todo}
              handleUpdate={handleUpdate}
              handleEdit={handleEdit}
              handleDelete={handleDelete}
            />
          </li>
        ))}
      </ul>
    </>
  );
};

export default TodoList;
