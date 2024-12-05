import { BrowserRouter, Route, Routes } from 'react-router-dom';

import UserRoute from './routes/UserRoute';
import AdminRoute from './routes/AdminRoute';
import NotFound from './pages/shared/NotFound';

function App() {
  return (
    <BrowserRouter>
        <Routes>
          <Route path='/*' element = {<UserRoute />} />
          <Route path='/admin/*' element = {<AdminRoute />} />
          
          <Route path='*' element = {<NotFound />} />
        </Routes>
    </BrowserRouter>
  );
}

export default App;