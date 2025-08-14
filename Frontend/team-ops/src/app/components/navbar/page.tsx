'use client';

import { useRouter } from "next/navigation";
import { useState } from "react";
import { 
  Home, 
  CheckSquare, 
  User, 
  Mail, 
  MessageSquare, 
  LogOut,
  Menu,
  X
} from "lucide-react";
import { Button } from "@/components/ui/button";

interface NavbarProps {
  activeRoute?: string;
}

export default function Navbar({ activeRoute = "/" }: NavbarProps) {
  const router = useRouter();
  const [isExpanded, setIsExpanded] = useState(true);

  const navigationItems = [
    {
      label: "Strona główna",
      icon: Home,
      route: "/",
      id: "home"
    },
    {
      label: "Zadania",
      icon: CheckSquare,
      route: "/tasks",
      id: "tasks"
    },
    {
      label: "Profil",
      icon: User,
      route: "/profile",
      id: "profile"
    },
    {
      label: "Skrzynka",
      icon: Mail,
      route: "/messages",
      id: "messages"
    },
    {
      label: "Anonimowe wiadomości",
      icon: MessageSquare,
      route: "/anonymous",
      id: "anonymous"
    }
  ];

  const handleLogout = async () => {
    try {
      await fetch("http://localhost:8081/api/logout", {
        method: "POST",
        credentials: "include"
      });
      router.push("/login");
    } catch (error) {
      console.error("Błąd podczas wylogowywania:", error);
    }
  };

  const handleNavigation = (route: string) => {
    router.push(route);
  };

  return (
    <div className={`fixed left-0 top-0 h-full bg-zinc-950 border-r border-zinc-800 transition-all duration-300 z-50 ${
      isExpanded ? 'w-72' : 'w-16'
    }`}>
      <div className="flex flex-col h-full">
        {/* Header with Logo */}
        <div className="p-4 border-b border-zinc-800">
          <div className="flex items-center justify-between">
            <div className={`flex items-center gap-3 transition-opacity duration-300 ${
              isExpanded ? 'opacity-100' : 'opacity-0'
            }`}>
              <h1 className="text-x2 font-bold text-white">TeamOps</h1>
            </div>
            <Button
              variant="ghost"
              size="sm"
              onClick={() => setIsExpanded(!isExpanded)}
              className="text-zinc-400 hover:text-white hover:bg-zinc-800 p-1 h-8 w-8"
            >
              {isExpanded ? <X className="w-4 h-4" /> : <Menu className="w-4 h-4" />}
            </Button>
          </div>
        </div>

        {/* Navigation Items */}
        <nav className="flex-1 px-3 py-4">
          <div className="space-y-2">
            {navigationItems.map((item) => {
              const Icon = item.icon;
              const isActive = activeRoute === item.route;
              
              return (
                <button
                  key={item.id}
                  onClick={() => handleNavigation(item.route)}
                  className={`w-full flex items-center gap-3 px-3 py-3 rounded-lg transition-all duration-200 group ${
                    isActive 
                      ? 'bg-white text-black' 
                      : 'text-zinc-400 hover:text-white hover:bg-zinc-800 '
                  }`}
                >
                  <Icon className={`w-5 h-5 flex-shrink-0 ${
                    isActive ? 'text-black' : 'text-zinc-400 group-hover:text-white'
                  }`} />
                  <span className={`font-medium transition-opacity duration-300 ${
                    isExpanded ? 'opacity-100' : 'opacity-0'
                  }`}>
                    {item.label}
                  </span>
                </button>
              );
            })}
          </div>
        </nav>

        {/* Logout Section */}
        <div className="p-3 border-t border-zinc-800">
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-3 py-3 rounded-lg text-zinc-400 hover:text-red-400 hover:bg-zinc-800 transition-all duration-200 group"
          >
            <LogOut className="w-5 h-5 flex-shrink-0 group-hover:text-red-400" />
            <span className={`font-medium transition-opacity duration-300 ${
              isExpanded ? 'opacity-100' : 'opacity-0'
            }`}>
              Wyloguj się
            </span>
          </button>
        </div>

        {/* Footer */}
        <div className="p-4 border-t border-zinc-800">
          <div className={`text-xs text-zinc-600 text-center transition-opacity duration-300 ${
            isExpanded ? 'opacity-100' : 'opacity-0'
          }`}>
            TeamOps v1.0
          </div>
        </div>
      </div>
    </div>
  );
}