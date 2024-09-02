import React, { useState, useEffect, useRef } from 'react';
import Styles from './CadastroCliente.module.css';
import { cpfMask } from '../components/Mask/CpfMask';
import CadastroRenda from './CadastroRenda';
import CadastroDespesa from './CadastroDespesa';
import CadastroPatrimonio from './CadastroPatrimonio';
import StylesTable from './Table.module.css';
import { ApiService } from '../services/appService';
import { useAuth } from '../contexto/AuthProvider';
import InputMask from 'react-input-mask';


const CadastroCliente = () => {

    // Dados iniciais fornecidos
    const initialData = {
        idCliente: 0,
        nome: '',
        cpf: '',
        dataNascimento: '',
        email: '',
        telefone: '',
        endereco: '',
    };

    // Opções fixas para o spcSerasa
    const simNao = [
        { valor: true, descricao: 'Sim' },
        { valor: false, descricao: 'Não' }
    ];

    const [nome, setNome] = useState(initialData.nome);
    const [cpf, setCpf] = useState(initialData.cpf);
    const [dataNascimento, setDataNascimento] = useState(initialData.dataNascimento);
    const [email, setEmail] = useState(initialData.email);
    const [telefone, setTelefone] = useState(initialData.telefone);
    const [endereco, setEndereco] = useState(initialData.endereco);
    const [autorizacaoLGPD, setAutorizacaoLGPD] = useState(false);
    const [idCliente, setIdCliente] = useState(initialData.idCliente);
    const [spcSerasa, setSpcSerasa] = useState(false);

    const spcSerasaRef = useRef(null);

    const [selectedSpcSerasa, setSelectedSpcSerasa] = useState(null);

    const [isSpcSerasaListVisible, setIsSpcSerasaListVisible] = useState(false);

    const [patrimonios, setPatrimonios] = useState([]);
    const { cpfLogado } = useAuth();

    const toggleListVisibility = (setter) => setter(prev => !prev);

    const handleOptionSelect = (option, setter, setVisibility) => {
        setter(option.descricao);
        setVisibility(false);
    };

    //Controle do patrimonio
    const addPatrimonio = () => {
        setPatrimonios([...patrimonios, {
            patrimonioTipo: {
                idPatrimonioTipo: 0,
                descricaoPatrimonioTipo: '',
            },
            valorPatrimonio: 0.0,
        },]);
    };

    const removePatrimonio = (index) => {
        setPatrimonios(patrimonios.filter((_, i) => i !== index));
    };
    const onUpdatePatrimonio = (index, value, name) => {
        const newPatrimonios = [...patrimonios];
        setNestedValue(newPatrimonios[index], name, value);
        setPatrimonios(newPatrimonios);
    };

    const validateEndereco = (endereco) => {
        if (!endereco || endereco.trim === '') {
            return 'O endereço é obrigatório.';
        }
        if (endereco.length < 3) {
            return 'O endereço deve conter no mínimo 3 letras.';
        }
    }

    const validateBirthDate = (birthDate) => {
        if (!birthDate || birthDate.trim === '') {
            return 'A data de nascimento é obrigatória.';
        }

        const today = new Date();
        const birth = new Date(birthDate);

        if (birth > today) {
            return 'A data de nascimento não pode ser maior do que a data atual.';
        }

        const ageDiffMs = today - birth;
        const ageDate = new Date(ageDiffMs);
        const age = Math.abs(ageDate.getUTCFullYear() - 1970);

        if (age < 18) {
            return 'Você precisa ter pelo menos 18 anos para se cadastrar.';
        }

        return null;
    };

    //Controle da renda
    const [rendas, setRendas] = useState([])
    const addRenda = () => {
        setRendas([...rendas, {
            rendaTipo: {
                idRendaTipo: 0,
                descricaoRendaTipo: '',
            },
            valorRenda: 0.0,
        },]);
    };

    const removeRenda = (index) => {
        setRendas(rendas.filter((_, i) => i !== index));
    };

    const onUpdateRenda = (index, value, name) => {
        const newRendas = [...rendas];
        setNestedValue(newRendas[index], name, value);
        setRendas(newRendas);
    };

    //Controle da despesa
    const [despesas, setDespesas] = useState([])
    const addDespesa = () => {
        setDespesas([...despesas, {
            despesaTipo: {
                idDespesaTipo: 0,
                descricaoDespesaTipo: '',
            },
            valorDespesa: 0.0,
        },]);
    };

    const removeDespesa = (index) => {
        setDespesas(despesas.filter((_, i) => i !== index));
    };
    const onUpdateDespesa = (index, value, name) => {
        const newDespesas = [...despesas];
        setNestedValue(newDespesas[index], name, value);
        setDespesas(newDespesas);
    };

    const setNestedValue = (obj, path, value) => {
        const keys = path.split('.'); // Divide o caminho em partes
        keys.reduce((acc, key, i) => {
            if (i === keys.length - 1) {
                acc[key] = value; // Atualiza o valor na última parte do caminho
            } else {
                if (!acc[key]) acc[key] = {}; // Cria o objeto se não existir
            }
            return acc[key];
        }, obj);
    };

    const formatDateToBR = (dateString) => {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Meses são baseados em zero
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
    };

    const [errors, setErrors] = useState({
        nome: null,
        cpf: null,
        dataNascimento: null,
        email: null,
        telefone: null,
        endereco: null,
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await ApiService.Get(`cliente/completo/${cpfLogado}`);
                setCpf(cpfLogado);
                // Formatar data para o formato 'yyyy-MM-dd'
                if (response && response.data) {
                    console.log("minha response", response);
                    const formattedDataNascimento = new Date(response.data.dataNascimento).toISOString().split('T')[0];
                    setIdCliente(response.data.idCliente);
                    setNome(response.data.nome);
                    // setCpf(response.data.cpf);
                    setDataNascimento(formattedDataNascimento);
                    setEmail(response.data.email);
                    setTelefone(response.data.telefone);
                    setEndereco(response.data.endereco);
                    setAutorizacaoLGPD(response.data.autorizacaoLGPD);
                    setSpcSerasa(response.data.spcSerasa);
                    setPatrimonios(response.data.patrimonios);
                    setRendas(response.data.rendas);
                    setDespesas(response.data.despesas);
                }
            } catch (error) {
                console.error('Erro ao buscar os dados do cliente:', error);
            }
        };

        fetchData();
    }, []);

    const validateCpf = (input) => {
        const cleanCpf = input.replace(/\D/g, ''); // Remove caracteres não numéricos
        const cpfPattern = /^[0-9]{11}$/;
        return cpfPattern.test(cleanCpf);
    };

    const validateName = (name) => {
        // Expressão regular que permite letras com e sem acentos e espaços
        const namePattern = /^[A-Za-zÀ-ÖØ-öø-ÿ\s']+$/;

        // Verifica se o nome tem entre 3 e 50 caracteres e se contém apenas letras e espaços
        return name.length >= 3 && name.length <= 50 && namePattern.test(name);
    };
    const validatePhone = (phone) => {
        const phonePattern = /^\d{11}$/; // Permite apenas 10 ou 11 dígitos
        return phonePattern.test(phone);
    };

    const validateEmail = (email) => {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Valida o formato do email
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
                if (value.trim() === '') {
                    errorMessage = 'Este campo é obrigatório.';
                }
                if (!validateName(value)) {
                    errorMessage = 'O nome deve ter entre 3 e 50 letras e pode conter apenas letras e espaços.';
                }
                break;
            case 'telefone':
                if (!validatePhone(value.replace(/\D/g, ''))) {
                    errorMessage = 'O telefone deve conter 11 dígitos numéricos.';
                }
                break;
            case 'email':
                if (!validateEmail(value)) {
                    errorMessage = 'O email deve ser um endereço válido.';
                }
                break;

            case 'dataNascimento':
                errorMessage = validateBirthDate(value);
                break;
            case 'endereco':
                errorMessage = validateEndereco(value);
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
        const formattedDataNascimento = dataNascimento; // Já está no formato 'yyyy-MM-dd'

        debugger;

        const formErrors = {
            nome: validateName(nome) ? null : 'O nome deve ter entre 3 e 50 letras e pode conter apenas letras e espaços.',
            cpf: validateCpf(cleanCpf) ? null : 'O CPF deve conter exatamente 11 dígitos numéricos.',
            dataNascimento: dataNascimento.trim() === '' ? 'Este campo é obrigatório.' : null,
            email: validateEmail(email) ? null : 'O email deve ser um endereço válido.',
            telefone: validatePhone(telefone.replace(/\D/g, '')) ? null : 'O telefone deve conter 11 dígitos numéricos.',
            endereco: endereco.trim() === '' ? 'Este campo é obrigatório.' : null,
        };
        setErrors(formErrors);
        if (Object.values(formErrors).every(error => error === null)) {
            const clienteData = {
                nome,
                cpf: cleanCpf,
                dataNascimento: formattedDataNascimento, // Enviando no formato 'yyyy-MM-dd'
                email,
                telefone,
                endereco,
                autorizacaoLGPD,
                spcSerasa: selectedSpcSerasa,
                despesas,
                patrimonios,
                rendas
            };

            const response = ApiService.Post(`cliente/completo-com-financeiro`, clienteData)
            if(clienteData != null){
                alert("AQUI");
            }
            setErrors({ nome: null, cpf: null, dataNascimento: null, email: null, telefone: null, endereco: null });
            // setNome('');
            // setCpf('');
            // setDataNascimento('');
            // setEmail('');
            // setTelefone('');
            // setEndereco('');
            // setAutorizacaoLGPD(false);
            // setSpcSerasa(false);
            // setRendas([]);
            // setPatrimonios([]);
            // setDespesas([]);               
        }
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
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.nome}</div>
                        </div>
                        <div className="br-input">
                            <label htmlFor="cpf">CPF</label>
                            <input
                                id="cpf"
                                type="text"
                                placeholder="Digite o CPF"
                                readOnly="true"
                                value={cpf}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.cpf}</div>
                        </div>
                        <div className="br-input">
                            <label htmlFor="dataNascimento">Data de Nascimento</label>
                            <input
                                id="dataNascimento"
                                type="date"
                                value={dataNascimento}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.dataNascimento}</div>
                        </div>
                        <div className="br-input">
                            <label htmlFor="email">Email</label>
                            <input
                                id="email"
                                type="email"
                                placeholder="Digite o email"
                                value={email}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            />
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.email}</div>
                        </div>
                        <div className="br-input">
                            <label htmlFor="telefone">Telefone</label>
                            <InputMask
                                id="telefone"
                                mask="(99) 99999-9999"
                                value={telefone}
                                onChange={handleInputChange}
                                onBlur={handleBlur}
                            >
                                {(inputProps) => <input {...inputProps} placeholder="Digite o telefone" />}
                            </InputMask>
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.telefone}</div>
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
                            <div style={{ color: 'red', fontSize: '0.875rem' }}>{errors.endereco}</div>
                        </div>
                        <div className="br-checkbox hidden-label">
                            <input id="check-all" name="check-all" type="checkbox"
                                checked={autorizacaoLGPD}
                                onChange={() => setAutorizacaoLGPD(!autorizacaoLGPD)} />
                            <label htmlFor="check-all">autorizacaoLgpd</label>
                            Eu autorizo o tratamento dos meus dados pessoais conforme a LGPD.
                        </div>

                        {/*INICIA OS DADOS FINANCEIROS*/}
                        <div className="br-input">
                            <h3>Dados Financeiros</h3>

                            {/* Restricao no Serasa */}
                            <div className="col-sm-20 col-lg-30 mb-2">
                                <label className="text-nowrap" htmlFor="spcSerasa">
                                    Possui restrição no SPC ou SERASA ? *
                                </label>
                                <div className={Styles.brselect} ref={spcSerasaRef}>
                                    <div className="br-input">
                                        <input
                                            id="spcSerasa"
                                            type="text"
                                            className="br-input"
                                            placeholder="Selecione uma opção"
                                            value={selectedSpcSerasa || ''}
                                            onClick={() => toggleListVisibility(setIsSpcSerasaListVisible)}
                                            readOnly
                                        />
                                        <button
                                            className="br-button"
                                            type="button"
                                            aria-label="Exibir lista"
                                            onClick={() => toggleListVisibility(setIsSpcSerasaListVisible)}
                                        >
                                            <i className="fas fa-angle-down" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    {isSpcSerasaListVisible && (
                                        <ul className={Styles.dropdownList}>
                                            {simNao.map(opcao => (
                                                <li
                                                    key={opcao.valor}
                                                    onClick={() => handleOptionSelect(opcao, setSelectedSpcSerasa, setIsSpcSerasaListVisible)}
                                                    className={Styles.dropdownItem}
                                                >
                                                    {opcao.descricao}
                                                </li>
                                            ))}
                                        </ul>
                                    )}
                                </div>
                            </div>
                        </div>
                        {<CadastroRenda rendas={rendas} onAddRenda={addRenda} onRemoveRenda={removeRenda} onUpdateRenda={onUpdateRenda} />}
                        {<CadastroPatrimonio patrimonios={patrimonios} onAddPatrimonio={addPatrimonio} onRemovePatrimonio={removePatrimonio} onUpdatePatrimonio={onUpdatePatrimonio} />}
                        {<CadastroDespesa despesas={despesas} onAddDespesa={addDespesa} onRemoveDespesa={removeDespesa} onUpdateDespesa={onUpdateDespesa} />}
                        <div className="p-3">
                            <button className="br-button primary mr-3" type="submit">Salvar</button>
                        </div>
                        {/* <button type="submit" className="br-button">Salvar</button> */}
                    </form>
                </div>

            </div>
        </>
    );
};

export default CadastroCliente;
