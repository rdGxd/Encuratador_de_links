import React, { ChangeEvent, useState } from "react";
import { registerAPI } from "@/services/user";

export const RegisterAccount = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [statusMessage, setStatusMessage] = useState<boolean>();
  const [errorMessage, setErrorMessage] = useState<string>();
  const [role, setRole] = useState("");

  const handleSubmitRegister = async () => {
    await registerAPI({ login, password, role }).then((response) => {
      if (response === 200) {
        setLogin("");
        setPassword("");
        setStatusMessage(true);
        setErrorMessage("Usuário criado com sucesso!");
      } else {
        setStatusMessage(false);
        setErrorMessage("Falha ao tentar criar usuário");
      }
    });
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setRole(e.target.value);
  };

  return (
    <>
      <div className="mt-5 flex w-full flex-wrap items-center justify-center lg:text-xl xl:text-2xl 2xl:text-3xl">
        <h2 className="w-full text-center text-4xl text-white">Register</h2>
        <form className="flex flex-wrap items-center p-5">
          <label
            htmlFor="register"
            className="my-2 mr-5 w-full text-white md:ml-14 lg:ml-32 xl:ml-44 2xl:ml-96"
          >
            Login
          </label>

          <input
            type={"text"}
            name={"registerAccount"}
            value={login}
            autoComplete="on"
            className={
              "w-full rounded p-2 md:ml-14 md:w-[85%] lg:ml-32 lg:w-[75%] lg:p-3 xl:ml-44 xl:w-[70%] 2xl:ml-96 2xl:w-[58%]"
            }
            placeholder={"Digite seu login"}
            minLength={3}
            maxLength={16}
            onChange={(e) => setLogin(e.target.value)}
          />

          <label
            htmlFor="password"
            className="my-2 mr-5 w-full text-white md:ml-14 lg:ml-32 xl:ml-44 2xl:ml-96"
          >
            Password
          </label>
          <input
            type={"password"}
            value={password}
            autoComplete="on"
            name={"password"}
            className={
              "w-full rounded p-2 md:ml-14 md:w-[85%] lg:ml-32 lg:w-[75%] lg:p-3 xl:ml-44 xl:w-[70%] 2xl:ml-96 2xl:w-[58%]"
            }
            placeholder={"Digite sua senha"}
            onChange={(e) => setPassword(e.target.value)}
            minLength={3}
            maxLength={16}
          />

          <button
            type={"button"}
            className={
              "mr-16 mt-4 rounded bg-white p-2 md:ml-16 lg:ml-32 xl:ml-44  2xl:ml-96"
            }
            onClick={handleSubmitRegister}
          >
            Registrar
          </button>
          <fieldset
            className={
              "mt-5 sm:flex sm:w-[50%] sm:justify-center sm:text-center lg:ml-60 lg:mt-0 xl:mt-4 "
            }
          >
            <legend className={"text-xl text-white xl:text-2xl 2xl:text-4xl"}>
              Selecione o cargo
            </legend>
            <div>
              <input
                type={"radio"}
                id={"ADMIN"}
                name={"Role"}
                value={"ADMIN"}
                className={"xl:h-1/3 xl:w-1/3"}
                checked={role === "ADMIN"}
                onChange={handleChange}
              />
              <label htmlFor="ADMIN" className={"text-white sm:mx-4 xl:flex"}>
                ADMIN
              </label>
            </div>
            <div>
              <input
                type={"radio"}
                id={"USER"}
                name={"Role"}
                value={"USER"}
                className={"xl:h-1/3 xl:w-1/3"}
                checked={role === "USER"}
                onChange={handleChange}
              />
              <label htmlFor="USER" className={"ml-4 text-white xl:ml-1"}>
                USER
              </label>
            </div>
          </fieldset>
          <span
            className={"md:ml-60"}
            style={{
              fontWeight: "bold",
              color: statusMessage ? "green" : "red",
              marginTop: "10px",
            }}
          >
            {errorMessage}
          </span>
        </form>
      </div>
    </>
  );
};
