import React, { KeyboardEvent, useEffect, useState } from "react";
import Router, { useRouter } from "next/router";
import validator from "validator";
import { sendLinkAPI } from "@/services/link";
import { cn } from "@/lib/utils";
import Image from "next/image";
import { Result } from "@/components/Result";
import { IconArrow } from "@/assets";
import { randomLink } from "@/util/RandomLink";

export default function Index() {
  const [inputValue, setInputValue] = useState("");
  const [valueToken, setValueToken] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState("");
  const [disabled, setDisable] = useState(false);
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/").then();
    }
    setValueToken(token as string);
  }, []);

  const handleKeyboardClick = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      if (validator.isURL(inputValue)) {
        const newURL = inputValue.replace("www.", "https://");
        await sendLinkAPI(randomLink(), newURL, valueToken);
        await sendLinkAPI(randomLink(), inputValue, valueToken);
        Router.reload();
      } else {
        setErrorMessage("URL inválida!");
      }
    }
  };

  const handleClick = async () => {
    if (validator.isURL(inputValue)) {
      const newURL = inputValue.replace("www.", "https://");
      await sendLinkAPI(randomLink(), newURL, valueToken);
      Router.reload();
      setDisable(!disabled);
      return;
    }
    setErrorMessage("Is Not Valid URL");
  };

  return (
    <main className="mt-16 flex flex-wrap justify-center ">
      <h1
        className={cn(
          "inline-block bg-gradient-to-r from-Brand-Primary-Pink  to-Brand-Primary-Blue bg-clip-text text-center text-4xl font-extrabold text-transparent lg:text-5xl xl:text-6xl",
        )}
      >
        Shorten Your Loooong Links
      </h1>
      <p className="mt-5 text-center text-gray-400 lg:text-xl xl:text-2xl 2xl:w-full">
        Linkly is an efficient and easy-to-use URL shortening service that
        streamlines your online experience.
      </p>
      <button
        type={"button"}
        className="absolute left-64  top-[322px] h-8 w-8 rounded-full border bg-Brand-Primary-Blue p-2 iPhoneSE:left-[300px] iPhoneSE:top-[300px] Pixel7:left-[350px] md:left-[700px] md:top-[235px] lg:left-[900px] lg:top-[250px] xl:left-[1300px] xl:top-[280px] 2xl:left-[1700px] 2xl:top-[265px]"
        onClick={handleClick}
        disabled={disabled}
        title="Enviar"
      >
        <Image src={IconArrow} alt={""} />
      </button>
      <label htmlFor={"input"}></label>
      <input
        name={"input"}
        placeholder={"Insira seu link aqui"}
        type={"text"}
        className={
          "mt-6 flex w-[95%] rounded-full border border-gray-400 bg-Grey p-4 text-center text-white lg:text-xl xl:ml-5 xl:text-3xl"
        }
        onChange={(e) => setInputValue(e.target.value)}
        onKeyDown={handleKeyboardClick}
      />
      <span
        style={{
          fontWeight: "bold",
          color: "red",
        }}
      >
        {errorMessage}
      </span>
      <Result />
    </main>
  );
}
