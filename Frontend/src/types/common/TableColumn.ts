export interface TableColumn<T> {
  name: string;
  selector: (row: T) => any;
  sortable: boolean;
}