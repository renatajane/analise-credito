import React, { useState } from 'react';
import Styles from './CadastroCliente.module.css'; 
import { cpfMask } from '../components/Mask/CpfMask';

const CadastroCliente = () => {
    const [cpf, setCpf] = useState('');
    const [message, setMessage] = useState(null);
    const [isBlurred, setIsBlurred] = useState(false);

    const validateCpf = (input) => {
        const cleanCpf = input.replace(/\D/g, ''); // Remove caracteres não numéricos
        const cpfPattern = /^[0-9]{11}$/;
        return cpfPattern.test(cleanCpf);
    };

    const handleInputChange = (event) => {
        const value = event.target.value;
        setCpf(cpfMask(value)); 
        if (!isBlurred) {
            // Não exibir mensagem até que o campo seja desfoqueado
            return;
        }
        // Validação será feita no onBlur
    };

    const handleBlur = () => {
        setIsBlurred(true);
        const cleanCpf = cpf.replace(/\D/g, ''); // Remove caracteres não numéricos para validação

        if (cleanCpf === '') {
            setMessage(null); // Não exibir mensagem se o campo estiver vazio
        } else if (!validateCpf(cleanCpf)) {
            setMessage({
                type: 'danger',
                title: 'Erro.',
                body: 'O CPF deve conter exatamente 11 dígitos numéricos.',
            });
        } else {
            setMessage(null); // Limpa a mensagem se o CPF for válido
        }
    };

    const renderMessage = () => {
        if (!message) return null;

        return (
            <div className={`br-message ${message.type}`}>
                <div className="icon">
                    <i className={`fas fa-${message.type === 'danger' ? 'times-circle' : 'info-circle'} fa-lg`} aria-hidden="true"></i>
                </div>
                <div className="content" aria-label={message.body} role="alert">
                    <span className="message-title">{message.title}</span>
                    <span className="message-body"> {message.body}</span>
                </div>
                <div className="close">
                    <button className="br-button circle small" type="button" aria-label="Fechar a mensagem alerta" onClick={() => setMessage(null)}>
                        <i className="fas fa-times" aria-hidden="true"></i>
                    </button>
                </div>
            </div>
        );
    };

    return (
        <div className={Styles.container}>
            <div className="col-sm-8 col-lg-5">
                <div className="br-input">
                    <label htmlFor="input-default">CPF</label>
                    <input
                        id="input-default"
                        type="text"
                        placeholder="Digite o CPF"
                        value={cpf}
                        onChange={handleInputChange}
                        onBlur={handleBlur}
                    />
                    <p>Texto auxiliar. Função de prevenir erros.</p>
                </div>
                {renderMessage()}
            </div>
        </div>
    );
};

export default CadastroCliente;
