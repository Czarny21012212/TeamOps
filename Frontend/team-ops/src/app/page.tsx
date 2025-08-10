"use client";
import { Button } from "@/components/ui/button"
import { useRouter } from "next/navigation";


export default function Home() {

  const router = useRouter();

  function handleLogin() {
    router.push("/authorization/login");
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-black text-white">
      <h1 className="text-4xl font-bold">Welcome to TeamOps</h1>
      <p className="mt-2 text-lg">Your team management solution.</p>
      <Button className= "m"onClick={handleLogin}>Get Started</Button>
      
    </div>
  );
}
