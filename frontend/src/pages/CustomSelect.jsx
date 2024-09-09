import React, { useState } from 'react';
import './CustomSelect.css'; // Certifique-se de que você tenha este arquivo CSS

const CustomSelect = ({ id, label, options, value, onChange }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState(value);

  const toggleDropdown = () => setIsOpen(!isOpen);

  const handleOptionChange = (e, option) => {
    const { checked } = e.target;
    let newSelectedOptions;
    if (checked) {
      newSelectedOptions = [...selectedOptions, option];
    } else {
      newSelectedOptions = selectedOptions.filter((item) => item !== option);
    }
    setSelectedOptions(newSelectedOptions);
    onChange(newSelectedOptions);
  };

  return (
    <div className="br-select" id={id}>
      <div className="br-input">
        <label htmlFor={id}>{label}</label>
        <input
          type="text"
          id={id}
          readOnly
          value={selectedOptions.join(', ')}
          placeholder="Selecione os itens"
          onClick={toggleDropdown}
        />
        <button
          className="br-button"
          type="button"
          aria-label="Exibir lista"
          onClick={toggleDropdown}
        >
          <i className="fas fa-angle-down" aria-hidden="true"></i>
        </button>
      </div>
      {isOpen && (
        <div className="br-list">
          <div className="br-item highlighted">
            <div className="br-checkbox">
              <input
                id={`select-all-${id}`}
                type="checkbox"
                checked={selectedOptions.length === options.length}
                onChange={(e) => {
                  if (e.target.checked) {
                    setSelectedOptions(options);
                    onChange(options);
                  } else {
                    setSelectedOptions([]);
                    onChange([]);
                  }
                }}
              />
              <label htmlFor={`select-all-${id}`}>Selecionar todos</label>
            </div>
          </div>
          {options.map((option) => (
            <div className="br-item" key={option.value}>
              <div className="br-checkbox">
                <input
                  id={option.value}
                  type="checkbox"
                  checked={selectedOptions.includes(option.label)}
                  onChange={(e) => handleOptionChange(e, option.label)}
                />
                <label htmlFor={option.value}>{option.label}</label>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CustomSelect;
