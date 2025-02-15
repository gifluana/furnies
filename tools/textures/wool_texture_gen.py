import os
import json
from PIL import Image

def load_palette(json_file):
    """Load the color palette from JSON."""
    with open(json_file, "r", encoding="utf-8") as f:
        return json.load(f)

def recolor_image(image_path, color_palette, output_folder):
    """Replace colors in light_blue.png using the palette and save new versions."""
    img = Image.open(image_path).convert("RGBA")
    pixels = img.load()
    width, height = img.size

    # Get the reference colors from light_blue
    reference_colors = [tuple(int(color[i:i+2], 16) for i in (1, 3, 5)) for color in color_palette["light_blue"]]

    for color_name, target_colors in color_palette.items():
        if color_name == "light_blue":
            continue
        
        target_colors_rgb = [tuple(int(color[i:i+2], 16) for i in (1, 3, 5)) for color in target_colors]

        new_img = img.copy()
        new_pixels = new_img.load()

        for x in range(width):
            for y in range(height):
                original_color = pixels[x, y][:3]  # Ignore alpha
                if original_color in reference_colors:
                    # Replace with the corresponding color from the new palette
                    index = reference_colors.index(original_color)
                    new_pixels[x, y] = target_colors_rgb[index] + (pixels[x, y][3],)  # Preserve alpha

        # Save the modified image
        new_image_path = os.path.join(output_folder, f"{color_name}.png")
        new_img.save(new_image_path)
        print(f"Saved: {new_image_path}")

def apply_palette_to_images(images_folder, json_palette):
    """Apply palette colors to all images."""
    color_palette = load_palette(json_palette)
    
    light_blue_path = os.path.join(images_folder, "light_blue.png")
    if not os.path.exists(light_blue_path):
        print("Error: 'light_blue.png' not found.")
        return

    recolor_image(light_blue_path, color_palette, images_folder)

# Example usage
images_folder = "wool"  # Folder containing images
json_palette = "wool_palette.json"  # JSON file with the color palette
apply_palette_to_images(images_folder, json_palette)
