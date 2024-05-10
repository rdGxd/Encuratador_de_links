import { useRouter } from "next/router";
import React, { KeyboardEvent, useEffect, useState } from "react";
import { updateLinkAPI } from "@/services/link";
import validator from "validator";
import { randomLink } from "@/util/RandomLink";

export default function update() {
  const [tokenValue, setTokenValue] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [newLink, setNewLink] = useState("");

  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) setTokenValue(token);
    if (!router.isReady) return;
  }, [router.isReady]);

  const handleChangeLink = async () => {
    if (validator.isURL(newLink)) {
      const newURL = newLink.replace("www.", "https://");
      await updateLinkAPI(tokenValue, id as string, randomLink(), newURL);
      await router.push("/links");
      return;
    } else {
      setErrorMessage("URL inválida");
    }
  };

  const handleKeyboardClick = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      if (validator.isURL(newLink)) {
        const newURL = newLink.replace("www.", "https://");
        await updateLinkAPI(tokenValue, id as string, randomLink(), newURL);
        await router.push("/links");
        return;
      } else {
        setErrorMessage("URL inválida");
      }
    }
  };

  return (
    <>
      <form className={" mt-5 text-center text-xl "}>
        <label
          htmlFor={"link"}
          className={"mb-5 flex w-full  justify-center text-white"}
        >
          Digite o novo link
        </label>
        <input
          value={newLink}
          name={"link"}
          id={"link"}
          className={"mb-10 w-[90%] rounded p-2 text-center"}
          onChange={(e) => setNewLink(e.target.value)}
          onKeyDown={(e) => handleKeyboardClick(e)}
        />
        <button
          className={"w-1/2 rounded bg-white p-2"}
          onClick={handleChangeLink}
          type={"button"}
        >
          Alterar
        </button>

        <span
          style={{
            fontWeight: "bold",
            color: "red",
          }}
        >
          {errorMessage}
        </span>
      </form>
    </>
  );
}
