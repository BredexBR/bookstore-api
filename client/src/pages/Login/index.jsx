import React, {useState} from 'react';
import { useNavigate} from 'react-router-dom';
import './styles.css';

import api from '../../services/api'

import logoImage from '../../assets/LogotipoBSA.png'
import BSAImageLogin from '../../assets/BSAImageLogin.png'

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e){
        e.preventDefault();

        const data = {
            username,
            password,
        };

        try {
            const response = await api.post('auth/signin', data);

            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.accessToken);
            localStorage.setItem('refreshToken', response.data.refreshToken);

            navigate('/books')
        } catch (error) {
            const errorMessage = error.response?.data?.message || 'Login failed! Try again!';
            alert(errorMessage);
        }
    };

    return (
        <div className="login-container">
            <section className="form">
                <img src={logoImage} alt="Erudio Logo"/>
                <form onSubmit={login}>
                    <h1>Access your Account</h1>
                    <input
                        placeholder="Username"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                    <input
                        type="password" placeholder="Password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />

                    <button className="button" type="submit">Login</button>
                </form>

            </section>

            <img src={BSAImageLogin} alt="Login"/>

        </div>
    )

}