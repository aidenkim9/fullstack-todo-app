import { useState } from "react";
import TodoList from "../components/TodoList";
import { useTodos } from "../hooks/useTodoQuery";
import { useTodoMutations } from "../hooks/useTodoMutations";
import { TodoAddForm } from "../components/TodoAddForm";

const TodoPage = () => {
  const [text, setText] = useState("");
  const [editId, setEditId] = useState(null);
  const [editText, setEditText] = useState("");

  const { data: todos, isLoading } = useTodos();
  const { addTodoMut, deleteTodoMut, updateTodoMut, toggleTodoMut } =
    useTodoMutations();

  const handleEdit = (todo) => {
    setEditId(todo.id);
    setEditText(todo.title);
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    addTodoMut.mutate(text);
    setText("");
  };

  const handleDelete = async (todo) => {
    // modal로 업그레이드
    if (!window.confirm("할 일을 삭제하겠습니까?")) return;

    deleteTodoMut.mutate(todo.id);
  };

  const handleUpdate = async (todo) => {
    updateTodoMut.mutate({
      id: todo.id,
      title: editText,
      completed: todo.completed,
    });

    setEditId(null);
  };

  const handleToggle = async (id) => {
    toggleTodoMut.mutate(id);
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div className="max-w-md mx-auto bg-white p-6 rounded-xl shadow">
          <h1 className="text-2xl font-bold mb-4">할일 목록</h1>
          <TodoAddForm handleAdd={handleAdd} text={text} setText={setText} />
          {todos.length === 0 ? (
            <div className="text-gray-400 text-center mt-10">
              새로운 할 일을 추가하세요!
            </div>
          ) : (
            <TodoList
              todos={todos}
              editId={editId}
              editText={editText}
              handleToggle={handleToggle}
              handleUpdate={handleUpdate}
              handleEdit={handleEdit}
              handleDelete={handleDelete}
              setEditId={setEditId}
              setEditText={setEditText}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default TodoPage;
