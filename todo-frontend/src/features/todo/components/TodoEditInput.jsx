export const TodoEditInput = ({
  todo,
  editText,
  setEditText,
  setEditId,
  handleUpdate,
}) => {
  return (
    <>
      <input
        value={editText}
        onChange={(e) => setEditText(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter") handleUpdate(todo);
          if (e.key === "Escape") setEditId(null);
        }}
        className="px-2 outline-none"
        autoFocus
      />
    </>
  );
};
