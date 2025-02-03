import os
import json

# Define the templates for recipes
def get_chair_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "chairs",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:stick"}
        },
        "pattern": [
            "# ",
            "##",
            "//"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_chair"
        }
    }

def get_sofa_recipe(color):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "sofas",
        "key": {
            "#": {"item": f"minecraft:{color}_wool"},
            "/": {"item": "minecraft:stick"}
        },
        "pattern": [
            "#  ",
            "###",
            "/ /"
        ],
        "result": {
            "count": 4,
            "id": f"furnies:{color}_sofa"
        }
    }

def get_stool_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "stools",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:stick"}
        },
        "pattern": [
            "##",
            "//"
        ],
        "result": {
            "count": 2,
            "id": f"furnies:{wood}_stool"
        }
    }

def get_table_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "tables",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:stick"}
        },
        "pattern": [
            "   ",
            "###",
            "/ /"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_table"
        }
    }

def get_bar_counter_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "bar_counters",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:calcite"}
        },
        "pattern": [
            "///",
            "###"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_bar_counter"
        }
    }

def get_cabinet_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "cabinets",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:barrel"}
        },
        "pattern": [
            "#/#",
            "###"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_cabinet"
        }
    }

def get_kitchen_cabinet_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "cabinets",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "/": {"item": "minecraft:barrel"}
        },
        "pattern": [
            "//",
            "##"
        ],
        "result": {
            "count": 4,
            "id": f"furnies:{wood}_kitchen_cabinet"
        }
    }

def get_kitchen_drawer_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "drawers",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "Q": {"item": f"minecraft:quartz_block"},
            "/": {"item": "minecraft:barrel"}
        },
        "pattern": [
            "QQQ",
            "#/#"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_kitchen_drawer"
        }
    }

def get_kitchen_cabinetry_recipe(wood):
    return {
        "type": "minecraft:crafting_shaped",
        "category": "building",
        "group": "cabinetry",
        "key": {
            "#": {"item": f"minecraft:{wood}_planks"},
            "Q": {"item": f"minecraft:quartz_block"}
        },
        "pattern": [
            "QQQ",
            "###"
        ],
        "result": {
            "count": 3,
            "id": f"furnies:{wood}_kitchen_cabinetry"
        }
    }

# Function to save JSON data to a file
def save_json_file(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=4)

# Main function
def generate_recipes():
    # Read colors and woods from JSON files
    script_dir = os.path.dirname(os.path.abspath(__file__))
    colors_file = os.path.join(script_dir, "colors.json")
    woods_file = os.path.join(script_dir, "woods.json")

    with open(colors_file, "r", encoding="utf-8") as f:
        colors = json.load(f)

    with open(woods_file, "r", encoding="utf-8") as f:
        woods = json.load(f)

    # Base directory for recipes
    base_dir = os.path.join(script_dir, "furnies", "recipes")

    # Generate recipes for colors
    # for color in colors:
        # sofa_path = os.path.join(base_dir, f"{color}_sofa.json")
        # save_json_file(sofa_path, get_sofa_recipe(color))

    # Generate recipes for woods
    for wood in woods:
        # chair_path = os.path.join(base_dir, f"{wood}_chair.json")
        # save_json_file(chair_path, get_chair_recipe(wood))

        # stool_path = os.path.join(base_dir, f"{wood}_stool.json")
        # save_json_file(stool_path, get_stool_recipe(wood))

        # cabinet_path = os.path.join(base_dir, f"{wood}_cabinet.json")
        # save_json_file(cabinet_path, get_cabinet_recipe(wood))

        # kitchen_cabinet_path = os.path.join(base_dir, f"{wood}_kitchen_cabinet.json")
        # save_json_file(kitchen_cabinet_path, get_kitchen_cabinet_recipe(wood))

        # kitchen_drawer_path = os.path.join(base_dir, f"{wood}_kitchen_drawer.json")
        # save_json_file(kitchen_drawer_path, get_kitchen_drawer_recipe(wood))

        kitchen_cabinetry_path = os.path.join(base_dir, f"{wood}_kitchen_cabinetry.json")
        save_json_file(kitchen_cabinetry_path, get_kitchen_cabinetry_recipe(wood))

        # bar_counter_path = os.path.join(base_dir, f"{wood}_bar_counter.json")
        # save_json_file(bar_counter_path, get_bar_counter_recipe(wood))

        # table_path = os.path.join(base_dir, f"{wood}_table.json")
        # save_json_file(table_path, get_table_recipe(wood))

if __name__ == "__main__":
    generate_recipes()
