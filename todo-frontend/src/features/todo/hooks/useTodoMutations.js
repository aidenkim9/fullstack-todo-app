import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createTodo, deleteTodo, updateTodo, toggleTodo } from "../api/todoApi";

export const useTodoMutations = () => {
  const queryClient = useQueryClient();

  const invalidate = () =>
    queryClient.invalidateQueries({ queryKey: ["todos"] });

  const addTodoMut = useMutation({
    mutationFn: createTodo,
    onMutate: async (title) => {
      await queryClient.cancelQueries(["todos"]);

      const prevTodos = queryClient.getQueryData(["todos"]);

      const tempId = Date.now();

      const optimisticTodo = {
        id: tempId,
        title,
        completed: false,
        optimistic: true,
      };

      queryClient.setQueryData(["todos"], (old = []) => [
        ...old,
        optimisticTodo,
      ]);

      console.log(prevTodos, optimisticTodo);

      return { prevTodos, optimisticTodo };
    },
    onError: (err, title, context) => {
      queryClient.setQueryData(["todos"], context.prevTodos);
    },

    onSuccess: (res, title, context) => {
      queryClient.setQueryData(["todos"], (old = []) =>
        old.map((todo) =>
          todo.id === context.optimisticTodo.id ? res.data : todo,
        ),
      );
    },

    onSettled: invalidate,
  });

  const deleteTodoMut = useMutation({
    mutationFn: deleteTodo,
    onMutate: async (id) => {
      await queryClient.cancelQueries(["todos"]);
      const prevTodos = queryClient.getQueryData(["todos"]);

      queryClient.setQueryData(["todos"], (old = []) =>
        old.filter((todo) => todo.id !== id),
      );

      return { prevTodos };
    },

    onError: (err, id, context) => {
      queryClient.setQueryData(["todos"], context.prevTodos);
    },

    onSettled: invalidate,
  });

  const updateTodoMut = useMutation({
    mutationFn: updateTodo,
    onMutate: async (data) => {
      await queryClient.cancelQueries(["todos"]);
      const prevTodos = queryClient.getQueryData(["todos"]);

      queryClient.setQueryData(["todos"], (old = []) =>
        old.map((todo) =>
          todo.id === data.id
            ? { ...todo, title: data.title, completed: data.completed }
            : todo,
        ),
      );

      return { prevTodos };
    },

    onError: (err, id, context) => {
      queryClient.setQueryData(["todos"], context.prevTodos);
    },
    onSettled: invalidate,
  });

  const toggleTodoMut = useMutation({
    mutationFn: toggleTodo,

    onMutate: async (id) => {
      console.log("onMute id: ", id);
      await queryClient.cancelQueries({ queryKey: ["todos"] });

      const previousTodos = queryClient.getQueryData(["todos"]) ?? [];

      console.log("prev", previousTodos);

      queryClient.setQueryData(["todos"], (old = []) => {
        console.log("old", old);
        return old.map((todo) =>
          todo.id === id ? { ...todo, completed: !todo.completed } : todo,
        );
      });

      return { previousTodos };
    },

    onError: (err, id, context) => {
      console.log("rollback context", context);

      queryClient.setQueryData(["todos"], context.previousTodos);
    },

    onSettled: invalidate,
  });

  return { addTodoMut, deleteTodoMut, updateTodoMut, toggleTodoMut };
};
