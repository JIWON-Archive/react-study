import { NavLink } from "react-router";

function MainPage() {
    return (
        <div className="text-3xl">
            <div>Main Page</div>
                <NavLink to="/about">About</NavLink>
        </div>
    );
}

export default MainPage;
