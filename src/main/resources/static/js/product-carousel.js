function customScrolling(direction, domId, size) {
  const container = document.querySelector(domId);
  if (direction === "left") {
    container.scrollBy({ left: -size, behavior: "smooth" });
  } else if (direction === "right") {
    container.scrollBy({ left: size, behavior: "smooth" });
  }
}
