import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/LogotipoBSA.png';

export default function Books() {
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);
    
    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    async function logout() {
        localStorage.clear();
        navigate('/');
    }

    async function editBook(id) {
        try {
            navigate(`/book/new/${id}`);
        } catch (error) {
            const errorMessage = error.response?.data?.message || 'Edit failed! Try again!';
            alert(errorMessage);
        }
    }

    async function deleteBook(id) {
        try {
            await api.delete(`api/book/v1/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });

            setBooks(books.filter(book => book.id !== id));
        } catch (err) {
            const errorMessage = err.response?.data?.message || 'Delete failed! Try again.';
            alert(errorMessage);
        }
    }

    useEffect(() => {
    let isMounted = true; // Controle de montagem

    async function loadBooks() {
        const token = localStorage.getItem('accessToken');
        if (!token) { navigate('/'); return; }

        try {
            const response = await api.get('api/book/v1', {
                headers: { Authorization: `Bearer ${token}` },
                params: { page: page, size: 4, direction: 'asc' }
            });

            if (isMounted && response.data?._embedded) {
                const newBooks = response.data._embedded.books;
                setBooks(prev => {
                    const filtered = newBooks.filter(n => !prev.some(p => p.id === n.id));
                    return [...prev, ...filtered];
                });
            }
        } catch (error) {
            if (isMounted) console.error("Erro ao carregar livros", error);
        }
    }

    loadBooks();

    return () => { isMounted = false; }; 
}, [page, navigate]);

    // O botão "Load More" APENAS avança a página. 
    // Como a página mudou, o useEffect acima será acionado automaticamente!
    function handleLoadMore() {
        setPage(prevPage => prevPage + 1);
    }
    
    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Erudio"/>
                <span>Welcome, <strong>{username?.toUpperCase()}</strong>!</span>
                <Link className="button" to="/book/new/0">Add New Book</Link>
                <button onClick={logout} type="button">
                    <FiPower size={18} color="#ff4b4b" />
                </button>
            </header>

            <h1>Registered Books</h1>
            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        <strong>Title:</strong>
                        <p>{book.title}</p>
                        <strong>Author:</strong>
                        <p>{book.author}</p>
                        <strong>Price:</strong>
                        <p>{Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                        <strong>Release Date:</strong>
                        <p>{Intl.DateTimeFormat('pt-BR').format(new Date(book.launchDate))}</p>
                        
                        <button onClick={() => editBook(book.id)} type="button">
                            <FiEdit size={20} color="#00d4ff"/> 
                        </button>
                        
                        <button onClick={() => deleteBook(book.id)} type="button">
                            <FiTrash2 size={20} color="#C4C4CC"/>
                        </button>
                    </li>
                ))}
            </ul>

            {/* Chamando a nova função simplificada */}
            <button className="button" onClick={handleLoadMore} type="button">Load More</button>
        </div>
    );
}