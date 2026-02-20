import { lazy, Suspense } from 'react';

// eslint-disable-next-line react-refresh/only-export-components
const Loading = () => <div>Loading...</div>;
const TodoIndex= lazy(() => import('../pages/todo/indexPage'));
const TodoList = lazy(() => import('../pages/todo/listPage'));
const ReadPage = lazy(() => import('../pages/todo/readPage'));

const todoRouter = () => {
    return ( 
        {
            path: 'todo',
            Component: TodoIndex,
            children: [
                {
                    path: 'list',
                    element:
                        <Suspense fallback={<Loading />}> <TodoList /> </Suspense>  
             }
            ]
        }
    )
}

export default todoRouter;