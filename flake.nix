{
  description = "Aircraft develop & build environment";
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

#   inputs.ivy2.url = "path:~/.ivy2/";

  outputs = { self, nixpkgs }:
    let
      pkgs = nixpkgs.legacyPackages.x86_64-linux;
    in {
      devShell.x86_64-linux = pkgs.mkShell {
        nativeBuildInputs = [ pkgs.bashInteractive ];
        buildInputs = [
            pkgs.jdk11
            pkgs.git
            pkgs.direnv
            (pkgs.mill.override { jre = pkgs.jdk11; })
        ];
        shellHook = ''
            export JAVA_HOME=${pkgs.jdk11}
            PATH="${pkgs.jdk11}/bin:$PATH"
        '';
      };

        packages.x86_64-linux.default =
        with import nixpkgs { system = "x86_64-linux"; };
        stdenv.mkDerivation {
            buildInputs = [
            pkgs.jdk11
            pkgs.git
            pkgs.direnv
            (pkgs.mill.override { jre = pkgs.jdk11; })
            ];
            name = "hello";
            src = self;
            buildPhase = "mill AircraftWar.jar";
            installPhase = 
            ''
            # mkdir -p $out/bin; 
            # install -t $out/bin hello
            '';
        };
    };

}