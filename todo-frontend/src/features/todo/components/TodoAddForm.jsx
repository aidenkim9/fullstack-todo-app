export const TodoAddForm = ({ handleAdd, text, setText }) => {
  return (
    <>
      <form onSubmit={handleAdd} className="flex gap-2 mb-4">
        <input
          className="flex-1 border rounded px-3 py-2"
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="할 일 입력"
        />
        <button className="bg-blue-500 text-white px-4 rounded">추가</button>
      </form>
    </>
  );
};
