import { Header } from "@/components/Header";
import { cn } from "@/lib/utils";
import { Head, Html, Main, NextScript } from "next/document";

export default function Document() {
  return (
    <Html lang="pt-br" title={"Encurtador de links"}>
      <Head title={"Encurtador de links"} />
      <body className={cn("bg-Black")}>
        <Header />
        <Main />
        <NextScript />
      </body>
    </Html>
  );
}
