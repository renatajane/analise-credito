import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Styles from './CadastroCliente.module.css'; // Verifique o caminho correto
import { cpfMask } from '../components/Mask/CpfMask'; // Verifique o caminho correto
import CadastroRenda from './CadastroRenda';
import CadastroDespesa from './CadastroDespesa';
import CadastroPatrimonio from './CadastroPatrimonio';
import StylesTable from './Table.module.css'; 

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

    const [nome, setNome] = useState(initialData.nome);
    const [cpf, setCpf] = useState(initialData.cpf);
    const [dataNascimento, setDataNascimento] = useState(initialData.dataNascimento);
    const [email, setEmail] = useState(initialData.email);
    const [telefone, setTelefone] = useState(initialData.telefone);
    const [endereco, setEndereco] = useState(initialData.endereco);
    const [autorizacaoLGPD, setAutorizacaoLGPD] = useState(false);
    const [idCliente, setIdCliente] = useState(initialData.idCliente);
    const [spcSerasa, setSpcSerasa] = useState(false); // Novo estado para restrição Serasa
 
    const [patrimonios, setPatrimonios] = useState([])
    //Controle do patrimonio
    const addPatrimonio = () => {
        setPatrimonios([...patrimonios,  {
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


//Controle da renda

const [rendas, setRendas] = useState([]) 
const addRenda = () => {
    setRendas([...rendas,  {   
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
    setDespesas([...despesas,  {   
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
                const response = await axios.get('http://localhost:8080/cliente/completo/12345655001')
                //     {
                //     // params: { cpf: '77239658007' } // Substitua pelo CPF que você deseja buscar
                // });

                // Preencha os estados com os dados recebidos
                setIdCliente(response.data.idCliente);
                setNome(response.data.nome);
                setCpf(response.data.cpf);
                setDataNascimento(response.data.dataNascimento);
                setEmail(response.data.email);
                setTelefone(response.data.telefone);
                setEndereco(response.data.endereco);
                setAutorizacaoLGPD(response.data.autorizacaoLGPD);
                setSpcSerasa(response.data.spcSerasa);
                setPatrimonios(response.data.patrimonio);
                setRendas(response.data.renda);
                setDespesas(response.data.despesa);

                console.log(response.data.patrimonio);
                // alert('Dados carregados com sucesso!');
            } catch (error) {
                console.error('Erro ao buscar os dados do cliente:', error);
                // alert('Erro ao buscar os dados do cliente.');
            }
        };

        fetchData();
    }, []); // O array vazio garante que a requisição seja feita apenas uma vez, quando o componente é montado.

    const validateCpf = (input) => {
        const cleanCpf = input.replace(/\D/g, ''); // Remove caracteres não numéricos
        const cpfPattern = /^[0-9]{11}$/;
        return cpfPattern.test(cleanCpf);
    };

    const validateName = (name) => {
        const namePattern = /^[A-Za-z\s]+$/; // Permite apenas letras e espaços
        return name.length >= 3 && name.length <= 50 && namePattern.test(name);
    };

    const validatePhone = (phone) => {
        const phonePattern = /^\d{10,11}$/; // Permite apenas 10 ou 11 dígitos
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
debugger;
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
                despesas,
                patrimonios,
                rendas
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
                    setSpcSerasa(false); // Limpar o estado do checkbox
                })
                .catch(error => {
                    alert('Ocorreu um erro ao cadastrar o cliente.');
                });
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
                                type="email"
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
                        <div className="br-checkbox hidden-label">
                            <input id="check-all" name="check-all" type="checkbox"
                                checked={autorizacaoLGPD}
                                onChange={() => setAutorizacaoLGPD(!autorizacaoLGPD)} />
                            <label htmlFor="check-all">autorizacaoLgpd</label>
                            Eu autorizo o tratamento dos meus dados pessoais conforme a LGPD.
                        </div>
                        <div className="br-input">
                            <h3>Dados Financeiros</h3>
                            <label>
                                Possui restrição no Serasa?
                            </label>
                            <div className="br-select">
                                <div className="br-input" id="select-container">
                                    <input id="select-simple" type="text" placeholder="Selecione o item" readOnly />
                                    <button className="br-button" type="button" aria-label="Exibir lista" tabIndex="-1">
                                        <i className="fas fa-angle-down" aria-hidden="true"></i>
                                    </button>
                                </div>
                                <div className="br-list" tabIndex="0">
                                    <div className="br-item" tabIndex="-1">
                                        <div className="br-radio">
                                            <input id="rb0" type="radio" name="estados-simples" value="rb0" />
                                            <label htmlFor="rb0">Sim</label>
                                        </div>
                                    </div>
                                </div>
                                <div className="br-item" tabIndex="-1">
                                    <div className="br-radio">
                                        <input id="rb1" type="radio" name="estados-simples" value="rb1" />
                                        <label htmlFor="rb1">Não</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {idCliente > 0 && <CadastroRenda rendas={rendas} onAddRenda={addRenda} onRemoveRenda={removeRenda} onUpdateRenda={onUpdateRenda} />}
                        {idCliente > 0 && <CadastroPatrimonio patrimonios={patrimonios} onAddPatrimonio={addPatrimonio} onRemovePatrimonio={removePatrimonio} onUpdatePatrimonio={onUpdatePatrimonio} />}
                        {idCliente > 0 && <CadastroDespesa  despesas={despesas} onAddDespesa={addDespesa} onRemoveDespesa={removeDespesa} onUpdateDespesa={onUpdateDespesa} />}
                        <button type="submit" className="br-button">Salvar</button>
                    </form>
                </div>
             
            </div>
        </>
    );
};

export default CadastroCliente;
