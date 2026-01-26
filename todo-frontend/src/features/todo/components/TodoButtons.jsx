import { Btn } from "../../../shared/ui/Btn";

export const TodoButtons = ({
  editId,
  todo,
  handleUpdate,
  handleEdit,
  handleDelete,
}) => {
  return (
    <div>
      {editId === todo.id ? (
        <Btn
          action="저장"
          variant="primary"
          actionFn={handleUpdate}
          todo={todo}
        />
      ) : (
        <Btn
          action="수정"
          variant="success"
          actionFn={handleEdit}
          todo={todo}
        />
      )}
      <Btn action="삭제" variant="danger" actionFn={handleDelete} todo={todo} />
    </div>
  );
};
