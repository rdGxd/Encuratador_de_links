import { useRouter } from "next/router";
import { useEffect } from "react";
import { LoginAccount } from "@/components/FormLogin";
import { RegisterAccount } from "@/components/FormRegister";

export default function Index() {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token !== null) {
      console.log("NOT NULL");
      router.push("/links").then();
    }
  }, []);

  return (
    <>
      <LoginAccount />
      <RegisterAccount />
    </>
  );
}
