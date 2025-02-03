import os
import json

# Function to generate blockstate
def get_blockstate(wood):
    return {
        "variants": {
        "facing=north": {
            "model": f"furnies:block/chair/{wood}"
        },
        "facing=east": {
            "model": f"furnies:block/chair/{wood}",
            "y": 90
        },
        "facing=south": {
            "model": f"furnies:block/chair/{wood}",
            "y": 180
        },
        "facing=west": {
            "model": f"furnies:block/chair/{wood}",
            "y": 270
        }
    }
    }

# Function to generate block models
def get_block_model(wood, suffix=""):
    return {
        "parent": f"furnies:block/template/chair{suffix}",
        "textures": {
            "1": f"furnies:block/chair/{wood}"
        }
    }

# Function to generate item model
def get_item_model(wood):
    return {
        "parent": "furnies:block/template/chair",
        "textures": {
            "1": f"furnies:block/chair/{wood}"
        }
    }

# Function to save JSON file
def save_json_file(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=4)

# Main function
def generate_files():
    # Read wood types from the JSON file in the same directory
    script_dir = os.path.dirname(os.path.abspath(__file__))
    woods_file = os.path.join(script_dir, "woods.json")

    with open(woods_file, "r", encoding="utf-8") as f:
        woods = json.load(f)

    # Base directory for furnies
    base_dir = os.path.join(script_dir, "furnies")

    # Generate files for each wood type
    for wood in woods:
        # Blockstate
        blockstate_path = os.path.join(base_dir, f"blockstates/{wood}_chair.json")
        save_json_file(blockstate_path, get_blockstate(wood))

        # Block models
        suffixes = [
            ""
        ]
        for suffix in suffixes:
            block_model_path = os.path.join(base_dir, f"models/block/chair/{wood}{suffix}.json")
            save_json_file(block_model_path, get_block_model(wood, suffix))

        # Item model
        item_model_path = os.path.join(base_dir, f"models/item/{wood}_chair.json")
        save_json_file(item_model_path, get_item_model(wood))

if __name__ == "__main__":
    generate_files()
