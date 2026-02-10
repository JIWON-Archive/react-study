import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router';
import BasicLayout from '../layouts/basicLayout';
// 페이지를 필요할 때만 로딩하기 위해 lazy와 Suspense 사용
const Loading = () => <div>Loading...</div>;
const Main = lazy(() => import('../pages/main-page'));
const About = lazy(() => import('../pages/aboutPage'));

const router = createBrowserRouter([
  // {
  //   path: "",
  //   element: <Suspense fallback={<Loading />}><Main /></Suspense>,
  // },
  // {
  //   path: "about",
  //   element: <Suspense fallback={<Loading />}><About /></Suspense>,
  // }
  {
    path: '',
    Component: BasicLayout,
    children: [
      {
        index: true,
        element: (
          <Suspense fallback={<Loading />}>
            <Main />
          </Suspense>
        ),
      },
      {
        path: 'about',
        element: (
          <Suspense fallback={<Loading />}>
            <About />
          </Suspense>
        ),
      },
    ],
  },
]);

export default router;
