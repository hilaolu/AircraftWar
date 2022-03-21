{
  description = "Aircraft develop & build environment";
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

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
      };
    };

}