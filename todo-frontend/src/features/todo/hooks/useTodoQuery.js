import { useQuery } from "@tanstack/react-query";
import { getTodo } from "../api/todoApi";

export const useTodos = () =>
  useQuery({
    queryKey: ["todos"],
    queryFn: getTodo,
  });
