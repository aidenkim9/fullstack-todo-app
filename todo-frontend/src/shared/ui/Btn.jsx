const variantMap = {
  primary: "bg-blue-500 hover:bg-blue-600",
  danger: "bg-rose-500 hover:bg-rose-600",
  success: "bg-teal-500 hover:bg-teal-600",
};

export const Btn = ({ action, variant, actionFn, todo }) => {
  return (
    <>
      <button
        className={`border p-1 rounded ${variantMap[variant]} text-white cursor-pointer`}
        onClick={() => actionFn(todo)}
      >
        {action}
      </button>
    </>
  );
};
