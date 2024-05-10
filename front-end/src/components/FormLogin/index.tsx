import { useState } from "react";
import { loginAPI } from "@/services/user";
import { useRouter } from "next/router";

export const LoginAccount = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  const submitLogin = async () => {
    await loginAPI({ login, password }).then((response) => {
      if (response === 200) {
        router.push("/links").then();
      }
    });
  };

  return (
    <>
      <div className="mt-10 flex w-full flex-wrap items-center justify-center lg:text-xl xl:text-2xl 2xl:text-3xl">
        <h2 className="w-full text-center text-4xl text-white">Login</h2>
        <form className="flex flex-wrap items-center p-5">
          <label htmlFor="login" className="mb-2 mr-5 w-full text-white">
            Login
          </label>
          <input
            type="text"
            name="login"
            autoComplete="on"
            value={login}
            id={"login"}
            className={"w-full rounded p-2 lg:p-3"}
            placeholder={"Digite seu login"}
            minLength={3}
            maxLength={16}
            onChange={(e) => setLogin(e.target.value)}
          />

          <label
            htmlFor="passwordLogin"
            className="my-2 mr-5 w-full text-white"
          >
            Password
          </label>
          <input
            type={"password"}
            value={password}
            name={"passwordLogin"}
            id={"passwordLogin"}
            autoComplete="on"
            className={"w-full rounded p-2 lg:p-3"}
            placeholder={"Digite sua senha"}
            minLength={3}
            maxLength={16}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button
            type={"button"}
            className={"mt-4 rounded bg-white p-2"}
            onClick={submitLogin}
          >
            Entrar
          </button>
        </form>
      </div>
    </>
  );
};
