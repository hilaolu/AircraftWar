{ pkgs ? import <nixpkgs> {}
}:
pkgs.mkShell{
  name="dev";
  buildInputs=[
    pkgs.git
    pkgs.direnv
    pkgs.jdk11
    (pkgs.mill.override { jre = pkgs.jdk11; })
  ];
}              
