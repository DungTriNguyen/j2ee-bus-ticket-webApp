import { useEffect, useState } from 'react';
import DataTable from 'react-data-table-component';
import { Dropdown, TextInput } from 'flowbite-react';

interface TableProps {
  columns: Array<{
    name: string;
    selector?: (row: any) => any;
    sortable?: boolean;
  }>;
  rows: Array<Record<string, any>>;
  searchableFields?: string[];
  onEdit?: (row: any) => void;
  onDelete?: (row: any) => void;
}

export default function Table({
  columns,
  rows,
  searchableFields = [],
  onEdit,
  onDelete,
}: TableProps) {
  const [data, setData] = useState(rows);
  const [search, setSearch] = useState('');

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.toLowerCase();
    setSearch(value);

    if (!rows || rows.length === 0) return;

    const fieldsToSearch =
      searchableFields.length > 0 ? searchableFields : Object.keys(rows[0] || {});

    const filteredData = rows.filter((row) =>
      fieldsToSearch.some((field) => {
        const fieldValue = row[field] && row[field].toString().toLowerCase();
        return fieldValue && fieldValue.includes(value);
      })
    );

    setData(filteredData);
  };

  useEffect(() => {
    setData(rows);
  }, [rows]);

  const actionColumn = {
    name: 'Actions',
    cell: (row: any) => (
      <Dropdown size='sm' inline label='Chỉnh sửa' dismissOnClick={false}>
        <Dropdown.Item onClick={() => onEdit && onEdit(row)}>Cập nhật</Dropdown.Item>
        <Dropdown.Item onClick={() => onDelete && onDelete(row)}>Xóa</Dropdown.Item>
      </Dropdown>
    ),
    ignorerowclick: true,
    allowoverflow: true,
  };

  return (
    <>
      <div className='flex flex-row-reverse'>
        <TextInput
          type='search'
          value={search}
          onChange={handleSearch}
          placeholder='Tìm kiếm...'
          className='mb-4'
        />
      </div>
      <div>
        <DataTable
          columns={[...columns, actionColumn]}
          data={data}
          fixedHeader
          pagination
          striped
        />
      </div>
    </>
  );
}
