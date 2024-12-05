import { Outlet } from "react-router-dom";
import MenuInfoUser from "../../components/user/MenuInfoUser";

export default function Dashboard() {
  return (
    <section className="flex justify-center items-center w-[90%] px-4 py-8 mx-auto">
      <div className="flex w-full gap-4 justify-between">
        {/* Left Side: Menu */}
        <div className="w-1/4">
          <MenuInfoUser />
        </div>

        {/* custom component will be render here based on path */}
        <div className="w-3/4 pl-8">
          <Outlet />
        </div>
      </div>
    </section>
  )
}