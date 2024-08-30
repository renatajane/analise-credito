import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Styles from './CadastroCliente.module.css'; // Verifique o caminho correto
import { cpfMask } from '../components/Mask/CpfMask'; // Verifique o caminho correto
import CadastroRenda from './CadastroRenda';
import CadastroDespesa from './CadastroDespesa';
import CadastroPatrimonio from './CadastroPatrimonio';
import StylesTable from './Table.module.css';

const CadastroCliente = () => {
    const initialData = {
        idCliente: 0,
        nome: '',
        cpf: '',
        dataNascimento: '',
        email: '',
        telefone: '',
        endereco: '',
    };

    const [nome, setNome] = useState(initialData.nome);
    const [cpf, setCpf] = useState(initialData.cpf);
    const [dataNascimento, setDataNascimento] = useState(initialData.dataNascimento);
    const [email, setEmail] = useState(initialData.email);
    const [telefone, setTelefone] = useState(initialData.telefone);
    const [endereco, setEndereco] = useState(initialData.endereco);
    const [autorizacaoLGPD, setAutorizacaoLGPD] = useState(false);
    const [idCliente, setIdCliente] = useState(initialData.idCliente);
    const [spcSerasa, setSpcSerasa] = useState(null); // Alterado para null para representar a ausência de seleção
    const [errors, setErrors] = useState({
        nome: null,
        cpf: null,
        dataNascimento: null,
        email: null,
        telefone: null,
        endereco: null,
    });
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/cliente/cpf/12345655001');
                setIdCliente(response.data.idCliente);
                setNome(response.data.nome);
                setCpf(response.data.cpf);
                setDataNascimento(response.data.dataNascimento);
                setEmail(response.data.email);
                setTelefone(response.data.telefone);
                setEndereco(response.data.endereco);
                setAutorizacaoLGPD(response.data.autorizacaoLGPD);
                setSpcSerasa(response.data.spcSerasa);
            } catch (error) {
                console.error('Erro ao buscar os dados do cliente:', error);
            }
        };

        fetchData();
    }, []);

    const validateCpf = (input) => {
        const cleanCpf = input.replace(/\D/g, '');
        const cpfPattern = /^[0-9]{11}$/;
        return cpfPattern.test(cleanCpf);
    };

    const validateName = (name) => {
        const namePattern = /^[A-Za-z\s]+$/;
        return name.length >= 3 && name.length <= 50 && namePattern.test(name);
    };

    const validatePhone = (phone) => {
        const phonePattern = /^\d{10,11}$/;
        return phonePattern.test(phone);
    };

    const validateEmail = (email) => {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailPattern.test(email);
    };

    const validateField = (fieldId, value) => {
        let errorMessage = null;

        switch (fieldId) {
            case 'cpf':
                const cleanCpf = value.replace(/\D/g, '');
                if (cleanCpf === '') {
                    errorMessage = null;
                } else if (!validateCpf(cleanCpf)) {
                    errorMessage = 'O CPF deve conter exatamente 11 dígitos numéricos.';
                }
                break;
            case 'nome':
                if (!validateName(value)) {
                    errorMessage = 'O nome deve ter entre 3 e 50 letras e pode conter apenas letras e espaços.';
                }
                break;
            case 'telefone':
                if (!validatePhone(value.replace(/\D/g, ''))) {
                    errorMessage = 'O telefone deve conter 10 ou 11 dígitos numéricos.';
                }
                break;
            case 'email':
                if (!validateEmail(value)) {
                    errorMessage = 'O email deve ser um endereço válido.';
                }
                break;
            case 'dataNascimento':
            case 'endereco':
                if (value.trim() === '') {
                    errorMessage = 'Este campo é obrigatório.';
                }
                break;
            default:
                break;
        }

        return errorMessage;
    };

    const handleInputChange = (event) => {
        const { id, value } = event.target;
        if (id === 'cpf') {
            setCpf(cpfMask(value));
        } else {
            switch (id) {
                case 'nome':
                    setNome(value);
                    break;
                case 'dataNascimento':
                    setDataNascimento(value);
                    break;
                case 'email':
                    setEmail(value);
                    break;
                case 'telefone':
                    setTelefone(value);
                    break;
                case 'endereco':
                    setEndereco(value);
                    break;
                default:
                    break;
            }
        }
    };

    const handleBlur = (event) => {
        const { id, value } = event.target;
        const errorMessage = validateField(id, value);
        setErrors((prevErrors) => ({
            ...prevErrors,
            [id]: errorMessage
        }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const cleanCpf = cpf.replace(/\D/g, '');
        const formErrors = {
            nome: validateName(nome) ? null : 'O nome deve ter entre 3 e 50 letras e pode conter apenas letras e espaços.',
            cpf: validateCpf(cleanCpf) ? null : 'O CPF deve conter exatamente 11 dígitos numéricos.',
            dataNascimento: dataNascimento.trim() === '' ? 'Este campo é obrigatório.' : null,
            email: validateEmail(email) ? null : 'O email deve ser um endereço válido.',
            telefone: validatePhone(telefone.replace(/\D/g, '')) ? null : 'O telefone deve conter 10 ou 11 dígitos numéricos.',
            endereco: endereco.trim() === '' ? 'Este campo é obrigatório.' : null,
        };

        setErrors(formErrors);

        if (Object.values(formErrors).every(error => error === null)) {
            const clienteData = {
                nome,
                cpf: cleanCpf,
                dataNascimento,
                email,
                telefone,
                endereco,
                autorizacaoLGPD,
                spcSerasa, // Inclua a nova propriedade no objeto de dados
            };

            axios.post('http://localhost:8080/cliente', clienteData)
                .then(response => {
                    setErrors({ nome: null, cpf: null, dataNascimento: null, email: null, telefone: null, endereco: null });
                    alert('Cliente cadastrado com sucesso.');
                    // Limpar o formulário ou redirecionar conforme necessário
                    setNome('');
                    setCpf('');
                    setDataNascimento('');
                    setEmail('');
                    setTelefone('');
                    setEndereco('');
                    setAutorizacaoLGPD(false);
                    setSpcSerasa(null); // Limpar o estado do seletor
                })
                .catch(error => {
                    alert('Ocorreu um erro ao cadastrar o cliente.');
                });
        }
    };

    const toggleSelectMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    };

    const handleSelectOption = (option) => {
        setSpcSerasa(option);
        setIsMenuOpen(false);
    };

    return (
        <>
            <div className={StylesTable.services}>
                <h3 className={StylesTable.color}>
                    Cadastro
                </h3>
            </div>
            <div className={Styles.container}>
                <h3 className="dados-pessoais">
                    Dados Pessoais
                </h3>
                <div className="col-sm-8 col-lg-5">
                    <form onSubmit={handleSubmit}>
                        <div className="br-input">
                            <label htmlFor="nome">Nome</label>
                            <input
                                id="nome"
                                type="text"
                                placeholder="Digite o nome"
                                value={nome}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.nome && <div className="error-message">{errors.nome}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="cpf">CPF</label>
                            <input
                                id="cpf"
                                type="text"
                                placeholder="Digite o CPF"
                                value={cpf}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.cpf && <div className="error-message">{errors.cpf}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="dataNascimento">Data de Nascimento</label>
                            <input
                                id="dataNascimento"
                                type="date"
                                placeholder="Digite a data de nascimento"
                                value={dataNascimento}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.dataNascimento && <div className="error-message">{errors.dataNascimento}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="email">Email</label>
                            <input
                                id="email"
                                type="text"
                                placeholder="Digite o email"
                                value={email}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.email && <div className="error-message">{errors.email}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="telefone">Telefone</label>
                            <input
                                id="telefone"
                                type="text"
                                placeholder="Digite o telefone"
                                value={telefone}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.telefone && <div className="error-message">{errors.telefone}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="endereco">Endereço</label>
                            <input
                                id="endereco"
                                type="text"
                                placeholder="Digite o endereço"
                                value={endereco}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            {errors.endereco && <div className="error-message">{errors.endereco}</div>}
                        </div>
                        <div className="br-input">
                            <label htmlFor="autorizacaoLGPD">
                                <input
                                    id="autorizacaoLGPD"
                                    type="checkbox"
                                    checked={autorizacaoLGPD}
                                    onChange={() => setAutorizacaoLGPD(!autorizacaoLGPD)}
                                />
                                Autorização LGPD
                            </label>
                        </div>
                        <div className="br-input">
                            <label htmlFor="spcSerasa">SPC/Serasa</label>
                            <div className={Styles.selectContainer}>
                                <button
                                    type="button"
                                    className={Styles.selectButton}
                                    onClick={toggleSelectMenu}
                                >
                                    {spcSerasa !== null ? (spcSerasa ? 'Sim' : 'Não') : 'Selecione'}
                                </button>
                                {isMenuOpen && (
                                    <ul className={Styles.selectMenu}>
                                        <li onClick={() => handleSelectOption(true)}>Sim</li>
                                        <li onClick={() => handleSelectOption(false)}>Não</li>
                                    </ul>
                                )}
                            </div>
                        </div>
                        <button type="submit">Salvar</button>
                    </form>
                </div>
                <CadastroRenda />
                <CadastroDespesa />
                <CadastroPatrimonio />
            </div>
        </>
    );
};

export default CadastroCliente;
