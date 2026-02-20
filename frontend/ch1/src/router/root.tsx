import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router';
import BasicLayout from '../layouts/basicLayout';
import todoRouter from './todoRouter';

// 로딩 중에 보여줄 임시 컴포넌트
// eslint-disable-next-line react-refresh/only-export-components
const Loading = () => <div>Loading...</div>;
// 페이지를 필요할 때만 로딩하기 위해 lazy와 Suspense 사용
// 컴포넌트를 필요할 때만 가져오는 지연 로딩(Lazy Loading) 설정
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
    Component: BasicLayout, // 모든 자식 페이지에 적용될 공통 레이아웃 (부모)
    children: [             // 레이아웃 내부의 <Outlet /> 자리에 들어갈 자식들
      {
        index: true,        // index: true는 path가 빈 문자열일 때 이 요소를 렌더링하라는 의미
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
      todoRouter(),
    ],
  },
]);

export default router;
